(ns clojure-rest.unit.model.document-model-test
  (:require [clojure.test :refer :all]
            [clojure-rest.model.document :as document]))

(def valid-document-map {:id_document "1" :title "title1" :description "text1"})
(def wrong-document-map-not-enough-keywords {:id_document "1" :title "title1"})
(def wrong-document-map-too-many-keywords {:id_document "1" :title "title1" :description "text1" :oneMoreKeyword "wrong"})
(def wrong-document-map-invalid-types {:id_document "1" :title 100 :description 1})

(deftest test-document-validation
  (testing "Testing document validation worked"
  (let [validation-res (document/validate-document-map valid-document-map)]
    (is (= validation-res valid-document-map))))

  (testing "Testing document validation failed with not enough keywords in map"
  (let [validation-res (document/validate-document-map wrong-document-map-not-enough-keywords)]
    (is (= validation-res nil))))

  (testing "Testing document validation failed with too many keywords in map"
  (let [validation-res (document/validate-document-map wrong-document-map-too-many-keywords)]
    (is (= validation-res nil))))

  (testing "Testing document validation failed with invalid types in map"
  (let [validation-res (document/validate-document-map wrong-document-map-invalid-types)]
    (is (= validation-res nil)))))
