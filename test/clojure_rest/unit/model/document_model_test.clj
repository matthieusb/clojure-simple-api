(ns clojure-rest.unit.model.document-model-test
  (:require [clojure.test :refer :all]
            [clojure-rest.model.document :as document]))

(def validDocumentMap {:id_document "1" :title "title1" :text "text1"})
(def wrongDocumentMapNotEnoughKeywords {:id_document "1" :title "title1"})
(def wrongDocumentMapTooManyKeywords {:id_document "1" :title "title1" :text "text1" :oneMoreKeyword "wrong"})
(def wrongDocumentMapInvalidTypes {:id_document "1" :title 100 :text 1})

(deftest test-document-validation
  (testing "Testing document validation worked"
  (let [validationRes (document/validate-document-map validDocumentMap)]
    (is (= validationRes validDocumentMap))))

  (testing "Testing document validation failed with not enough keywords in map"
  (let [validationRes (document/validate-document-map wrongDocumentMapNotEnoughKeywords)]
    (is (= validationRes nil))))

  (testing "Testing document validation failed with too many keywords in map"
  (let [validationRes (document/validate-document-map wrongDocumentMapTooManyKeywords)]
    (is (= validationRes nil))))

    (testing "Testing document validation failed with invalid types in map"
    (let [validationRes (document/validate-document-map wrongDocumentMapInvalidTypes)]
      (is (= validationRes nil))))

  )
