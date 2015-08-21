(ns blackify.core
  (:use mikera.image.colours)
  (:use mikera.image.core)
  (:use mikera.image.filters)
  (:use mikera.image.spectrum)
  (:require [clojure.java.io :refer [resource]])
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:import [java.awt Color])
  (:gen-class))

(defn black-or-white
  [color]
  (.getRGB (if (< (.getGreen (new Color color)) (* 0.5 255))
    Color/BLACK
    Color/WHITE)))

(def cli-options
  ;; An option with a required argument
  [["-t" "--tolerance TOLERANCE" "Tolerance"
    :default 0.5
    :parse-fn #(Double/parseDouble %)
    :validate [#(< 0 % 1) "Must be a number between 0 and 1"]]
   ;; A boolean option defaulting to nil
   ["-i" "--inverted"]])

(defn blackify
  [options]
  (let [count (-> "count.png" clojure.java.io/file load-image)]
    (set-pixels count
      (into-array Integer/TYPE
        (map
          #(.getRGB
            (if
              ((if (get options :inverted) > <)
              (.getGreen (new Color %))
              (* (get options :tolerance) 255))
            Color/BLACK
            Color/WHITE))
          (get-pixels ((grayscale) count)))))
    (show count :title "foobar")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println (first args))
  (blackify (get (parse-opts args cli-options) :options)))
