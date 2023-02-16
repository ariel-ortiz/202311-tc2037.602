;
(ns first-example)

; First function
(defn add1
  [x]
  (+ x 1))

(add1 41)
(add1 -10)
(add1 (add1 50))
