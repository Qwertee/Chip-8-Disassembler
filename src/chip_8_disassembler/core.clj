(ns chip-8-disassembler.core
  (:require [chip-8-disassembler.util :refer :all])
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def file-contents (read-file-as-bytes "/home/jon/code/cplusplus/Chip8Emulator/games/PONG"))

(defn unknown-instr
  [instr]
  (println (format "ERROR: unknown instruction specified: %X%X" (:left-byte instr) (:right-byte instr))))

(defn handle-0x0
  [instr]
  (if (= 0xE (:y instr))
    (if (= 0xE (:n instr))
      "RET"
      "CLS")
    "SYS"))

(defn handle-0x8
  [instr]
  (let [x (:x instr)
        y (:y instr)]
    (case (:n instr)
      0x0 (format "LD V%X, V%X" x y)

      0x1 (format "OR V%X, V%X" x y)

      0x2 (format "AND V%X, V%X" x y)

      0x3 (format "XOR V%X, V%X" x y)

      0x4 (format "ADD V%X, V%X" x y)

      0x5 (format "SUB V%X, V%X" x y)

      0x6 (format "SHR V%X {, V%X} ; VF <- 1 if lsb lf V%X is 1" x y x)

      0x7 (format "SUBN V%X, V%X" x y)

      0xE (format "SHL V%X {, V%X} ; VF <- 1 if msb lf V%X is 1" x y x)

      (unknown-instr instr))))

(defn handle-instruction
  [instr]
  (let []
    (case (:id instr)
      0x0 (handle-0x0 instr)

      0x1 (format-12-bits instr "JP")

      0x2 (format-12-bits instr "CALL")

      0x3 (format "SE V%X, 0x%X" (:x instr) (:right-byte instr))

      0x4 (format "SNE V%X, 0x%X" (:x instr) (:right-byte instr))

      0x5 (format "SE V%X, V%X" (:x instr) (:y instr))

      0x6 (format "LD V%X, 0x%X" (:x instr) (:right-byte instr))

      0x7 (format "ADD V%X, 0x%X" (:x instr) (:right-byte instr))

      0x8 (str 'UNKNOWN)

      0x9 (format "SNE V%X, V%X" (:x instr) (:y instr))

      0xA (format "LD I, 0x%X" (combine-nibbles (:x instr)
                                                (:y instr)
                                                (:n instr)))

      0xB (format "JP V0, 0x%X" (combine-nibbles (:x instr)
                                                 (:y instr)
                                                 (:n instr)))

      0xC (format "RND V%X, byte ; set to rand byte AND 0x%X" (:x instr) (:right-byte instr))

      0xD (format "DRW V%X, V%X, 0x%X" (:x instr) (:y instr) (:n instr))

      ; TODO: There is another 0xE instruction to handle!
      0xE (format "SKNP V%X" (:x instr))

      0xF (str 'UNKNOWN))))

(defn disassemble
  [instructions]
  (map handle-instruction instructions))
