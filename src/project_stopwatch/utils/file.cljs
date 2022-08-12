(ns project-stopwatch.utils.file
  (:require [clojure.core.async :as async]
            [cljs.core.async.interop :refer [<p!]]))

(defn open-dir
  "Open directory"
  [create]
  (async/go (<p! (.showDirectoryPicker js/window #js {"create" create}))))

(defn open-dir-with-path
  "Open directory with a pre-defined path"
  [create path] 
  (async/go (<p! (.showDirectoryPicker js/window #js {"startIn" path "create" create}))))

(defn open-file
  "Open file"
  [create]
  (async/go (<p! (.showFilePicker js/window #js {"create" create}))))

(defn open-file-with-path
  "Open file with a pre-defined path"
  [create path]
  (async/go (<p! (.showFilePicker js/window #js {"startIn" path "create" create}))))

(defn open-file-from-dir
  "Open file from directory"
  [create directory file]
  (async/go (<p! (.getFileHandle directory file #js {"create" create}))))

(defn read-file
  "Read file"
  [file]
  (async/go (<p! (.text (.getFile file)))))

(defn write-file
  "Write file"
  [create file content]
  (async/go
   (let [writable (<p! (.createWritable file))]
     (.write writable content)
     (.close writable))))