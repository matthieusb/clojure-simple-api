(ns clojure-rest.config.database
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:require [clojure.java.jdbc :as jdbc]))

(def db
      {:classname "org.h2.Driver"
       :subprotocol "h2"
       :subname "mem:documents"
       :user "sa"
       :password ""})

(jdbc/db-do-commands db
      (jdbc/create-table-ddl :documents
                            [[:id "integer" "primary key"]
                            [:title "varchar(1024)"]
                            [:text "varchar(2048)"]]))
