(ns turing-machines
  (:require [clojure.test :refer [deftest is run-tests]])
  (:import (java.io Writer)))

(defrecord TM [intial-state accept-states transitions])

(defrecord Tape [left head right]
  Object
  (toString [_] (format "%s[%s]%s" left head right)))

(defmethod print-method Tape
  [self ^Writer writer]
  (.write writer (str self)))

; (->Tape "aaaa" \b "cccc")

(defn make-tape
  ([s]
   (let [s (drop-while #(= % \_) s)]
     (make-tape ""
                (if (= s "")
                  \_
                  (first s))
                (rest s))))
  ([left head right]
   (let [left (drop-while #(= % \_) left)
         right (reverse (drop-while #(= % \_) (reverse right)))]
     (->Tape (apply str left)
             head
             (apply str right)))))

; (make-tape "____h_o_l_a____")

(defn write-tape
  [{:keys [left right]} value]
  (make-tape left value right))

; (write-tape (make-tape "ho" \l "a") \x)

;(defn shift-head
;  [{:keys [left head right]} direction]
;  (case direction
;    :right (make-tape (str left head) (or (first right) \_) (rest right))
;    :left (make-tape)
;    (throw (ex-info (str "Bad direction: " direction) {}))))


(def tm-1 (->TM :q0
                #{:q2}
                {:q0 {\a [\a :right :q1]
                      \_ [\_ :left :q2]}
                 :q1 {\a [\a :right :q0]}}))
