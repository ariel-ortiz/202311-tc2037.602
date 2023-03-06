(ns more-repetitions
  (:require [clojure.test :refer [deftest is run-tests]]))

;; Problem 1
(defn expand
  [s]
  (mapcat #(repeat %2 %1)
          s
          (range 1 (inc (count s)))))

(deftest test-expand
  (is (= () (expand ())))
  (is (= '(a) (expand '(a))))
  (is (= '(1 2 2 3 3 3 4 4 4 4) (expand '(1 2 3 4))))
  (is (= '(a b b c c c d d d d e e e e e)
         (expand '(a b c d e)))))

;; Problem 2
;(defn insert
;  [n s]
;  (cond
;    (empty? s) (list n)
;    (<= n (first s)) (cons n s)
;    :else (cons (first s) (insert n (rest s)))))

(defn insert
  [n s]
  (let [[menores mayores] (split-with #(< % n) s)]
    (concat menores (list n) mayores)))

(deftest test-insert
  (is (= '(14) (insert 14 ())))
  (is (= '(4 5 6 7 8) (insert 4 '(5 6 7 8))))
  (is (= '(1 3 5 6 7 9 16) (insert 5 '(1 3 6 7 9 16))))
  (is (= '(1 5 6 10) (insert 10 '(1 5 6)))))

;; Problem 3
;(defn insertion-sort
;  [s]
;  (if (empty? s)
;    ()
;    (insert (first s)
;            (insertion-sort (rest s)))))

(defn insertion-sort
  [s]
  (reduce #(insert %2 %1)
          ()
          s))

(deftest test-insertion-sort
  (is (= () (insertion-sort ())))
  (is (= '(0 1 3 3 4 6 7 8 9)
         (insertion-sort '(4 3 6 8 3 0 9 1 7))))
  (is (= '(1 2 3 4 5 6) (insertion-sort '(1 2 3 4 5 6))))
  (is (= '(1 5 5 5 5 5 5) (insertion-sort '(5 5 5 1 5 5 5)))))

(run-tests)
