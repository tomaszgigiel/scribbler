(ns pl.tomaszgigiel.scribbler.core
  (:require [clojure.edn :as edn])
  (:require [clojure.tools.logging :as log])
  (:require [pl.tomaszgigiel.scribbler.cmd :as cmd])
  (:require [pl.tomaszgigiel.scribbler.common :as common])
  (:gen-class))

(defn- work [path]
  (let [props (-> path slurp edn/read-string)
        param-a (:param-a props)
        param-b (:param-b props)]
    ))

(defn -main [& args]
  "scribbler: scribbler"
    (let [{:keys [uri options exit-message ok?]} (cmd/validate-args args)]
      (if exit-message
        (cmd/exit (if ok? 0 1) exit-message)
        (log/info (work (first args))))
      (log/info "ok (clojure)")
      (shutdown-agents)))
