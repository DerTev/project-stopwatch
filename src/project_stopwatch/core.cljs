(ns project-stopwatch.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [clojure.core.async :as async]
   [cljs.core.async.interop :refer [<p!]]))

(def directory (r/atom nil))

(defn get-data
  "Get data from data-file"
  []
  (async/go
    (let [data-file (<p! (.getFileHandle @directory "data.json" #js {"create" true}))]
      (println (str "Text: " (.-text data-file))))))

(defn write-data
  "Write data to data-file"
  []
  )

(defn update-main
  "Update Main-Screen"
  []
  (d/render [:table {:class "table"} 
             [:thead 
              [:tr
               [:th {:scope "col"} "Projekt"]
               [:th {:scope "col"} "Datum"]
               [:th {:scope "col"} "Zeit"]
               [:th {:scope "col"} "Honorar"]
               [:th {:scope "col"} "Aktionen"]]]
             [:tbody
              [:tr
               [:td "Test"]
               [:td "20.07.2022"]
               [:td "1"]
               [:td "100"]
               [:td [:i {:class "fa-solid fa-trash" :style {:cursor "pointer"}}]]]]] (.getElementById js/document "main")))

(defn open-dir
  "Open new directory"
  []
  (async/go (reset! directory(<p! (.showDirectoryPicker js/window)))
            (update-main)
            (get-data)))

; Init app
(defn mount-root []
  (d/render [:div [:div {:class ["header"]}
              [:h1 "Project Stopwatch"]
              (if (boolean (.-chrome js/window)) [:div {:id "actions"} 
                                                  [:i {:onClick open-dir :class "fa-solid fa-folder-open" :style {:cursor "pointer"}}] 
                                                  [:i {:onClick #(.reload js/location) :class "fa-solid fa-folder-closed ms-2" :style {:cursor "pointer"}}]
                                                  [:i {:onClick #(js/alert "Backup wurde erfolgreich erstellt!") :class "fa-solid fa-folder-tree ms-2" :style {:cursor "pointer"}}]])]
             [:br]
             [:div {:id ["main"]}
              (if (boolean (.-chrome js/window))
                [:p "Es wurde noch kein Ordner geöffnet." [:br] "Bitte öffnen Sie einen Ordner, um zu starten!"]
                [:p "Bitte öffnen Sie diese Seite mit einem " [:a {:href "https://en.wikipedia.org/wiki/Chromium_(web_browser)#Active" :target :blank} "chromium basierenden Browser"] "!"])]] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
