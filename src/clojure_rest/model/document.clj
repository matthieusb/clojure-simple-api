(ns clojure-rest.model.document
  (:use [korma.core :only [defentity table entity-fields pk]])
  (:require [clojure.tools.logging :as log]
            [schema.core :as schema]))

; -- Korma configuration
(defentity documents
  (table :documents) ; Associated table
  (pk :id_document) ; primary key
  (entity-fields :id_document :title :text)) ; Default field for select

; -- Validation schema
(def document-schema
  {:id_document schema/Str
   :title schema/Str
   :text schema/Str})

(defn validate-document-map
  "Creates a new document from a map, with validation"
  [map-input]
  (try (schema/validate document-schema map-input)
    (catch Exception e (log/error (str "Validation exception occured : " (.getMessage e))))))
