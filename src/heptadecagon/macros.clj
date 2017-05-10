(ns heptadecagon.macros)
 
(defmacro convert [fn-name]
  (let [x (gensym)]
    `(defn ~(symbol fn-name) [~x & args#]
       (. ~x (~(symbol fn-name) ~@args#))))) 
       ;(apply ~(symbol (str x "." fn-name)) (cons ~x args#)))))

