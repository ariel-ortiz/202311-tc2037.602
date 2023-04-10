(ns parallelism)

; Problem 1
;
; SPEED UP CALCULATION
;
; n = 100,000
; p = 8
;
; Run #1   T1 = 3298.514416    Tp = 170.713583
; Run #2   T1 = 3242.947208    Tp = 176.793
; Run #3   T1 = 3257.167042    Tp = 196.700583
; Run #4   T1 = 3253.873208    Tp = 187.150583
; Run #5   T1 = 3278.489542    Tp = 180.2875
; Average  T1 = 3266.198283    Tp = 182.3290498
;
; Sp = T1/Tp = 17.91375695

(defn bits
  [x]
  (.bitCount (biginteger x)))

(defn fact-seq
  [n]
  (bits (loop [i 2
               r 1]
          (if (> i n)
            r
            (recur (inc i) (*' r i))))))

(defn fact-ranges
  [n p]
  (partition 2 1 (concat (range 1 n (quot n p))
                         [(inc n)])))

(defn fact-partial
  [[start end]]
  (loop [i start
         r 1]
    (if (= i end)
      r
      (recur (inc i) (*' r i)))))

(defn fact-par
  [n]
  (as-> (.availableProcessors (Runtime/getRuntime)) RESULT
        (fact-ranges n RESULT)
        (pmap fact-partial RESULT)
        (reduce *' RESULT)
        (bits RESULT)))

(time (fact-seq 100000))

(time (fact-par 100000))