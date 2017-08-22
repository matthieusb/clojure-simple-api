(ns clojure-rest.model.document)

(defrecord Document [id_document title text])

(defn document
  "Creates a new document record"
  [{:keys [id_document title text]]})
