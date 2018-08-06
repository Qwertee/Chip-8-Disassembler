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


(defn get-nibbles
  [b]
  {:left (left-nibble b)
   :right (right-nibble b)})

(defn combine-nibbles
  "Combines the passed nibbles together to form a single value. The most significant nibble
  (The one that should end up in the leftmost position) should be passed first"
  [& nibbles]
  (reduce (fn [accumulator x]
            (bit-or x (bit-shift-left accumulator 4))) 0x0 nibbles))

(defn parse-instruction
  [instr-bytes]
  (let [left-byte (first instr-bytes)
        right-byte (second instr-bytes)]
    {:left-byte  (combine-nibbles (left-nibble left-byte) (right-nibble left-byte))
     :right-byte (combine-nibbles (left-nibble right-byte) (right-nibble right-byte))
     :id         (left-nibble left-byte)
     :x          (right-nibble left-byte)
     :y          (left-nibble right-byte)
     :n          (right-nibble right-byte)}))

(defn get-instructions [arr] (map parse-instruction (partition 2 2 arr)))

(defn format-12-bits
  [instr message]
  (format "%s 0x%X" message (combine-nibbles (:x instr)
                                             (:y instr)
                                             (:n instr))))
