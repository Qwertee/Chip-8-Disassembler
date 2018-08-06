# chip-8-disassembler

Simple disassembler for the Chip-8 architecture.
Pass it a binary file for the Chip-8 system, and this will output a much more readble assembly file.
Currently does not support Super Chip-48 instructions.

## Installation

Make sure that you have leiningen installed.

Clone this repo: https://github.com/Qwertee/Chip-8-Disassembler

Run

    $ lein bin

to pull all dependencies and build a self-contained executable within the 
`target/default` directory.

## Usage

    $ ./target/default/chip8d [PATH_TO_BIN]
    
Will write the disassembled contents of the specified file to `output.asm` within the current directory.

## Options

Will eventually support the ability to specify an output file.

## License

Copyright © 2018 - Jon Berrend

Distributed under the MIT License.
