(ns clojure-rest.config.database
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:require [clojure.java.jdbc :as sql]))

(def db
      {:classname "org.h2.Driver"
       :subprotocol "h2"
       :subname "mem:documents"
       :user "sa"
       :password ""})

(sql/db-do-commands db
      (sql/create-table-ddl :documents
                            [:id "integer" "primary key"]
                            [:title "varchar(1024)"]
                            [:text :varchar]))
