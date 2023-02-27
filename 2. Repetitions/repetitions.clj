(ns repetitions
  (:require [clojure.test :refer [deftest is run-tests]])
  (:require [clojure.math.numeric-tower :refer [sqrt]])
  (:require [clojure.algo.generic.math-functions
             :refer [sqr approx=]]))

;; Recursive version
;(defn enlist
;  [x]
;  (if (empty? x)
;    x
;    (cons (list (first x))
;          (enlist (rest x)))))

;; Loop/recur version
;(defn enlist
;  [x]
;  (loop [y x
;         r ()]
;    (if (empty? y)
;      (reverse r)
;      (recur (rest y)
;             (cons (list (first y))
;                   r)))))

;; Problem 1
;; Sequence API version
(defn enlist
  [x]
  (map list x))

(deftest test-enlist
  (is (= () (enlist ())))
  (is (= '((a) (b) (c)) (enlist '(a b c))))
  (is (= '((1) (2) (3) (4)) (enlist [1 2 3 4])))
  (is (= '(((1 2 3)) (4) ((5)) (7) (8))
         (enlist '((1 2 3) 4 (5) 7 8)))))

;; Problem 2
(defn positives
  [s]
  (filter pos? s))

(deftest test-positives
  (is (= () (positives ())))
  (is (= () (positives [-4 -1 -10 -13 -5])))
  (is (= [3 6] (positives [-4 3 -1 -10 -13 6 -5])))
  (is (= [4 3 1 10 13 6 5] (positives [4 3 1 10 13 6 5]))))

;; Problem 3
(defn square-and-add
  [accum x]
  (+ accum (sqr x)))

(defn add-squares
  [s]
  (reduce square-and-add 0 s))

(deftest test-add-squares
  (is (= 0 (add-squares [])))
  (is (= 25 (add-squares [5])))
  (is (= 30 (add-squares [2 4 1 3])))
  (is (= 385 (add-squares [1 2 3 4 5 6 7 8 9 10]))))

;; Problem 4
(defn twice
  [x]
  (list x x))

(defn duplicate
  [s]
  (mapcat twice s))

(deftest test-duplicate
  (is (= [1 1 2 2 3 3 4 4 5 5]
         (duplicate [1 2 3 4 5])))
  (is (= ()
         (duplicate ())))
  (is (= '(a a)
         (duplicate '(a))))
  (is (= '(a a b b c c d d e e f f g g h h)
         (duplicate '(a b c d e f g h)))))

;; Problem 5
(defn fib
  [n]
  (loop [a 0
         b 1
         i 0]
    (if (= i n)
      a
      (recur b (+' a b) (inc i)))))

(deftest test-fib
  (is (= 0
         (fib 0)))
  (is (= 1
         (fib 1)))
  (is (= 1
         (fib 2)))
  (is (= 5
         (fib 5)))
  (is (= [0 1 1 2 3 5 8 13 21 34 55 89 144 233 377 610
          987 1597 2584 4181 6765]
         (map fib (range 21))))
  (is (= 267914296
         (fib 42))))

(run-tests)
