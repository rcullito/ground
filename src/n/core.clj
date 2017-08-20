(ns n.core)

(defn handle-n-form
  [threader g step]
  (let [post-n-step (second step)]
    `(if (nil? ~g) nil
         (if (~threader ~g ~post-n-step)
           ~g
           nil))))

(defmacro n
  "like some->, except forms following n are treated as predicates
  that do not affect the value passed to the next form."
  [expr & forms]
  (let [g (gensym)
        threader (if (sequential? expr)
                   '->>
                   '->)
        steps (map (fn [step]
                     (if ((every-pred seq? #(= (first %) 'n)) step)
                       (handle-n-form threader g step)
                       `(if (nil? ~g) nil (~threader ~g ~step))))
                   forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))

