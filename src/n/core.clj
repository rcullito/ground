(ns n.core)

(defmacro thread-smart
  "if the current expr is sequential, thread last"
  [expr form]
  `(if (sequential? ~expr)
     (->> ~expr ~form)
     (-> ~expr ~form)))

(defn handle-n-form
  "handle the 'pass-through' functionality"
  [g step]
  (let [post-n-step (second step)]
    `(if (nil? ~g)
       nil
       (if (thread-smart ~g ~post-n-step)
         ;; this is the pass through
         ;; i.e. rather than evaluating the current form
         ;; we pass along the previous value: g
         ~g
         nil))))

(defn step-fn
  "if the current form begins with an n, followed by another form
  handle it differently, otherwise threading functions normally"
  [g step]
  (if ((every-pred seq? #(= (first %) 'n)) step)
    (handle-n-form g step)
    `(if (nil? ~g)
       nil
       (thread-smart ~g ~step))))

(defmacro n
  "like some->, except forms following n are treated as predicates
  that do not affect the value passed to the next form."
  [expr & forms]
  (let [g (gensym)
        steps (map (partial step-fn g) forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))

