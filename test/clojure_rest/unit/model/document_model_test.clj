(ns clojure-rest.unit.model.document-model-test
  (:require [clojure.test :refer :all]
            [clojure-rest.model.document :as document]))

(deftest test-document-validation
  (testing "Testing document validation worked")
  (let [validationRes (document/validate-document-map {:id_document "1" :title "title1" :text "text1"})])
  )
