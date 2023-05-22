(ns analisis-lexico)

; (slurp "entrada.txt")

(def er #"(?xi)
    ( -? \d+ \. \d* (?: e -? \d+)? ) # Grupo 1: Flotante
  | ( [a-z] \w* )                    # Grupo 2: Variables
  | ( // .* )                        # Grupo 3: Comentarios
  | ( \d+ )                          # Grupo 4: Entero
  | ( [=] )                          # Grupo 5: Asignación
  | ( [+] )                          # Grupo 6: Suma
  | ( [-] )                          # Grupo 7: Resta
  | ( [*] )                          # Grupo 8: Multiplicación
  | ( [/] )                          # Grupo 9: División
  | ( \^ )                           # Grupo 10: Potencia
  | ( [(] )                          # Grupo 11: Parénteses que abre
  | ( [)] )                          # Grupo 12: Paréntesis que cierra
  | ( \s )                           # Grupo 13: Espacios
  | ( . )                            # Grupo 14: Carácter inválido
")

(defn tokenize
  [input]
  (map (fn [token]
         (cond
           (token 1) [:flotante (token 0)]
           (token 2) [:variable (token 0)]
           (token 3) [:comentario (token 0)]
           (token 4) [:entero (token 0)]
           (token 5) [:asignacion (token 0)]
           (token 6) [:suma (token 0)]
           (token 7) [:resta (token 0)]
           (token 8) [:multiplicacion (token 0)]
           (token 9) [:division (token 0)]
           (token 10) [:potencia (token 0)]
           (token 11) [:paren-izq (token 0)]
           (token 12) [:paren-der (token 0)]
           (token 14) [:error (token 0)]))
       (remove (fn [v] (v 13)) (re-seq er input))))

(defn tokenize-file
  [file-name]
  (tokenize (slurp file-name)))

(defn separador
  []
  (println (apply str (repeat 56 \=))))

(def termino {:flotante "Flotante"
              :variable "Variable"
              :comentario "Comentario"
              :entero "Entero"
              :asignacion "Asignación"
              :suma "Suma"
              :resta "Resta"
              :multiplicacion "Multiplicación"
              :division "División"
              :potencia "Potencia"
              :paren-izq "Paréntesis que abre"
              :paren-der "Paréntesis que cierra"
              :error "Token inválido"})

(defn print-table
  [file-name]
  (separador)
  (println (format "%-32sTipo" "Token"))
  (separador)
  (doseq [token (tokenize-file file-name)]
    (println (format "%-32s%s"
                     (token 1)
                     (termino (token 0)))))
  (separador))


;; (spit "salida.txt" (with-out-str (print-table "entrada.txt")))

(def plantilla-hmtl
  "
<!DOCTYPE html>
<html lang=\"es\">
  <head>
    <title>Análisis Léxico</title>
    <style>
      body {
        background-color: beige;
      }
      table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
        padding-left: 10px;
        padding-top: 5px;
      }
      .flotante {
        color: blue;
      }
      .entero {
        color: red;
      }
      .comentario {
        color: green;
      }
    </style>
  </head>
  <body>
    <h1>Análisis Léxico</h1>
    <table>
      <tr>
        <th>Token</th>
        <th>Tipo</th>
      </tr>
      %s
    </table>
  </body>
</html>
  ")

(defn htmlize
  [lst]
  (map (fn [[t v]]
         (format "<tr><td><span class=\"%s\">%s</span></td><td>%s</td></tr>"
                 (symbol t)
                 v
                 (termino t)))
       lst))

(defn txt->html
  [in-name out-name]
  (spit out-name
        (format plantilla-hmtl
                (clojure.string/join
                  "\n"
                  (htmlize (tokenize (slurp in-name)))))))

(txt->html "entrada.txt" "entrada.html")
