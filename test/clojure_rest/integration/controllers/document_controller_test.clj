(ns clojure-rest.integration.controllers.document-controller-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure-rest.handler :refer :all]
            [clojure-rest.config.database :as database]))

(def expectedHeaders {"Content-Type" "application/json; charset=utf-8"})
(def initialDocumentList (json/generate-string [{:id_document "1" :title "titre1" :text "text1"} {:id_document "2" :title "titre2" :text "text2"}]))
(def documentToCreate {:id_document "dummy" :title "newDocumentTitle" :text "newDocumentText"})
(def documentToUpdate {:id_document "1" :title "titre1Updated" :text "texte1Updated"})

(deftest test-document-controller-list
  (database/init-database)

  (testing "Testing document list route worked"
    (let [response (app (mock/request :get "/documents"))]
      (is (= (:status response) 200))
      (is (= (:headers response) expectedHeaders))
      (is (= (:body response) initialDocumentList)))))

(deftest test-document-controller-getbyid
  (database/init-database)
  (testing "Testing get document by id route worked"
    (let [response (app (mock/request :get "/documents/1"))]
      (is (= (:status response) 200))))

  (testing "Testing get document by id route failed with incorrect id"
    (let [response (app (mock/request :get "/documents/notfound"))]
      (is (= (:status response) 404)))))

(deftest test-document-controller-create
  (database/init-database)

  (testing "Testing document creation route worked"
    (let [request (mock/request :post "/documents" (json/generate-string documentToCreate))]
    (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 200))
      (is (= (get (json/parse-string (:body response)) "title") (:title documentToCreate)))
      (is (= (get (json/parse-string (:body response)) "text") (:text documentToCreate)))
      (is (= (count (jdbc/query database/db-h2-connection ["select * from documents"])) 3)))))

  (testing "Testing document creation route failed because of incorrect document"
    (let [request (mock/request :post "/documents" (json/generate-string {:id_document "dummy" :title "newDocumentTitle" :text "newDocumentText" :wrongattribute "badvalue"}))]
    (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 400))))))

(deftest test-document-controller-update
  (database/init-database)

  (testing "Testing document update route worked"
    (let [request (mock/request :put "/documents/1" (json/generate-string documentToUpdate))]
      (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 200))
      (is (= (get (json/parse-string (:body response)) "title") (:title documentToUpdate)))
      (is (= (get (json/parse-string (:body response)) "text") (:text documentToUpdate))))))

  (testing "Testing document update route failed with incorrect id"
    (let [request (mock/request :put "/documents/notfound" (json/generate-string {:id_document "noid" :title "wontwork" :text "causeIncorrectid"}))]
      (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 404)))))

  (testing "Testing document update route failed with incorrect document format"
    (let [request (mock/request :put "/documents/invalidformat" (json/generate-string {:id_document "incorrectformat" :title "wontwork"}))]
      (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 400))))))

(deftest test-document-controller-delete
  (database/init-database)
  (testing "Testing document delete route worked"
    (let [response (app (mock/request :delete "/documents/1"))]
      (is (= (:status response) 204))
      (is (= (count (jdbc/query database/db-h2-connection ["select * from documents"])) 1))))

  (database/init-database)
  (testing "Testing document delete route failed"
    (let [response (app (mock/request :delete "/documents/notfound"))]
      (is (= (:status response) 404)))))
