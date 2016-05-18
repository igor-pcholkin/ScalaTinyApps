package sound

import javax.sound.midi.MidiSystem

object MidiInstrumentsInfo extends App {
  val synth = MidiSystem.getSynthesizer();
  val instruments = synth.getAvailableInstruments
  
  instruments.zipWithIndex map { case (instr, i) => println(s"$i: ${instr.getName}") }
}