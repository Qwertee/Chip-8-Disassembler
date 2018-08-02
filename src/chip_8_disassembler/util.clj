(ns chip-8-disassembler.util
  (import (java.nio.file Paths Files)
          (java.net URI)))

(defn left-nibble
  [byte]
  (unsigned-bit-shift-right (bit-and byte 0xF0) 4))

(defn right-nibble
  [byte]
  (bit-and byte 0x0F))

(defn read-file-as-bytes
  "Reads a file ands returns a byte array corresponding with its contents"
  [filename]
  (let [path (Paths/get (URI. (str "file:///" filename)))]
    (Files/readAllBytes path)))

(defn get-instructions [arr] (partition 2 2 arr))

(defn get-nibbles
  [b]
  {:left (left-nibble b)
   :right (right-nibble b)})