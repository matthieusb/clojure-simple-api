(ns clojure-rest.integration.controllers.document-controller-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [clojure-rest.handler :refer :all]
            [clojure-rest.config.database :as database]))

(def expectedHeaders {"Content-Type" "application/json; charset=utf-8"})
(def initialDocumentList (json/generate-string [{:id_document "1" :title "titre1" :text "text1"} {:id_document "2" :title "titre2" :text "text2"}]))

(deftest test-document-controller-list
  (database/initDatabase)

  (testing "Testing document list route worked"
    (let [response (app (mock/request :get "/documents"))]
      (is (= (:status response) 200))
      (is (= (:headers response) expectedHeaders))
      (is (= (:body response) initialDocumentList)))))

(deftest test-document-controller-getbyid
  (database/initDatabase)
  (testing "Testing get document by id route worked"
    (let [response (app (mock/request :get "/documents/1"))]
      (is (= (:status response) 200))))

  (testing "Testing get document by id route failed with incorrect id"
    (let [response (app (mock/request :get "/documents/notfound"))]
      (is (= (:status response) 404)))))

(deftest test-document-controller-create
  (database/initDatabase)

  (testing "Testing document creation route worked"
    (let [request (mock/request :post "/documents" (json/generate-string {:id_document "dummy" :title "newDocumentTitle" :text "newDocumentText"}))]
    (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 200)))))
    ) ; TODO Add body test and count test

  ; TODO
  ; (testing "Testing document creation route failed"
  ;   (let [response (app (mock/request :post "/documents"))]
  ;     (is (= (:status response) 200))))



; (deftest test-document-controller-update
;   (database/initDatabase)
;
;   (testing "Testing document update route worked"
;     (let [response (app (mock/request :put "/documents/1"))]
;       (is (= (:status response) 200))))
;
;   (testing "Testing document update route failed"
;     (let [response (app (mock/request :put "/documents/1"))]
;       (is (= (:status response) 200)))))


; (deftest test-document-controller-delete
;   (database/initDatabase)
;
;   (testing "Testing document delete route worked"
;     (let [response (app (mock/request :delete "/documents/1"))]
;       (is (= (:status response) 200))))
;
;   (testing "Testing document delete route failed"
;     (let [response (app (mock/request :delete "/documents/1"))]
;       (is (= (:status response) 200)))))
