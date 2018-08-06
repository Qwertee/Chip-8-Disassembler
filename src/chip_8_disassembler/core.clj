(ns chip-8-disassembler.core
  (:require [chip-8-disassembler.util :refer :all])
  (:gen-class))

(defn unknown-instr
  [instr]
  (println (format "ERROR: unknown instruction specified: 0x%X%X"
                   (:left-byte instr)
                   (:right-byte instr))))

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

(defn handle-0xE
  [instr]
  (let [right-byte (:right-byte instr)]
    (case right-byte
      0x9E (format "SKP V%X"  (:x instr))

      0xA1 (format "SKNP V%X" (:x instr))

      (unknown-instr instr))))

(defn handle-0xF
  [instr]
  (let [x (:x instr)]
    (case (:right-byte instr)
      0x07 (format "LD V%X, DT" x)

      0x0A (format "LD V%X, K" x)

      0x15 (format "LD DT, V%X" x)

      0x18 (format "LD ST, V%X" x)

      0x1E (format "ADD I, V%X" x)

      0x29 (format "LD F, V%X" x)

      0x33 (format "LD B, V%X" x)

      0x55 (format "LD [I], V%X" x)

      0x65 (format "LD V%X, [I]" x)

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

      0x8 (handle-0x8 instr)

      0x9 (format "SNE V%X, V%X" (:x instr) (:y instr))

      0xA (format "LD I, 0x%X" (combine-nibbles (:x instr)
                                                (:y instr)
                                                (:n instr)))

      0xB (format "JP V0, 0x%X" (combine-nibbles (:x instr)
                                                 (:y instr)
                                                 (:n instr)))

      0xC (format "RND V%X, byte ; set to rand byte AND 0x%X" (:x instr) (:right-byte instr))

      0xD (format "DRW V%X, V%X, 0x%X" (:x instr) (:y instr) (:n instr))

      0xE (handle-0xE instr)

      0xF (handle-0xF instr)

      (unknown-instr instr))))

(defn disassemble
  [file-contents]
  (map handle-instruction (get-instructions file-contents)))

(defn write-to-file
  [string file-name]
  (spit file-name string))

(defn -main
  "Entry point for program."
  [& args]
  (println "Warning, this disassembler does not currently support Super Chip-48 at the moment...")
  (when (not= 1 (count args))
    (println "ERROR: please specify path to file as only command line arg.")
    (System/exit 1))
  (println "disassembling...")
  (let [string (clojure.string/join "\n" (disassemble (read-file-as-bytes (first args))))]
    (println "writing...")
    (write-to-file string "output.asm")))
