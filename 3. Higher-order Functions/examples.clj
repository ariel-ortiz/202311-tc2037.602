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
