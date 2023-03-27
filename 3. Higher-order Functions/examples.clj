(ns examples)

; Función de orden mayor (higher-order function)
; es aquella que cumple con 1 o 2 de siguientes condiciones:
; 1) Recibe como entrada (argumentos) una o más funciones.
; 2) Devuelve una función como resultado.

(defn aplica-2-veces
  [fun x]
  (fun (fun x)))

(aplica-2-veces inc 5)
(aplica-2-veces number? 42)
(aplica-2-veces #(* % %) 2)

(defn suma-acum
  [n]
  (fn [x] (+ x n)))

(suma-acum 5)
((suma-acum 5) 6)
(def f (suma-acum 10))
(f 5)
(f 34)
(def g (suma-acum 50))
(g 1)
(f 1)

(defn compuesta
  [f g]
  (fn [x]
    (f (g x))))

(def f1 (fn [x] (* x x)))
(def f2 (fn [x] (+ x 3)))
(def f3 (compuesta f1 f2))
(def f4 (compuesta f2 f1))
(def f5 (compuesta f3 f4))
(def f6 (compuesta f4 f3))
(f1 1)
(f2 1)
(f3 1)
(f4 1)
(f5 1)
(f6 1)

(defn k
  [a b c d e]
  (+ a b c d e))

(defn k-curry
  [a]
  (fn [b]
    (fn [c]
      (fn [d]
        (fn [e]
          (+ a b c d e))))))

(k 1 2 3 4 5)
(((((k-curry 1) 2) 3) 4) 5)
