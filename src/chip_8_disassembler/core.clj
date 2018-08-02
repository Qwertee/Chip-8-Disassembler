(ns chip-8-disassembler.core
  (:require [chip-8-disassembler.util :refer :all])
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(def file-contents (read-file-as-bytes "/home/jon/code/cplusplus/Chip8Emulator/games/PONG"))

(defn disassemble
  [instructions]
  (map handle-instruction instructions))

(defn handle-0x0
  [instr]
  (if (= 0xE (:y instr))
    (if (= 0xE (:n instr))
      "RET"
      "CLS")
    "SYS"))

(defn handle-0x1
  [instr]
  (format-12-bits instr "JP"))

(defn handle-0x2
  [instr]
  (format-12-bits instr "CALL"))

(defn handle-instruction
  [instr]
  (let [leftmost-nibble (left-nibble (first instr))]
    (case leftmost-nibble
      0x0 (handle-0x0 instr)
      0x1 (handle-0x1 instr)
      0x2 ()
      0x3 ()
      0x4 ()
      0x5 ()
      0x6 ()
      0x7 ()
      0x8 ()
      0x9 ()
      0xA ()
      0xB ()
      0xC ()
      0xD ()
      0xE ()
      0xF ())))