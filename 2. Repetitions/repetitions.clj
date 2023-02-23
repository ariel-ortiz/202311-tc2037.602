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
(defn enlist
  [x]
  (loop [y x
         r ()]
    (if (empty? y)
      (reverse r)
      (recur (rest y)
             (cons (list (first y))
                   r)))))

(deftest test-enlist
  (is (= () (enlist ())))
  (is (= '((a) (b) (c)) (enlist '(a b c))))
  (is (= '((1) (2) (3) (4)) (enlist [1 2 3 4])))
  (is (= '(((1 2 3)) (4) ((5)) (7) (8))
         (enlist '((1 2 3) 4 (5) 7 8)))))

(run-tests)
