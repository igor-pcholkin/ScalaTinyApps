package sound

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem

object Sintesizer extends App {
    val buf = Array[Byte](0, 0)
    val (sampleRate, bitRate, nChannels, signedData, bigEndian, seconds, amplitude) = (44100f, 16, 1, true, false, 5, 100)
    val audioFormat = new AudioFormat( sampleRate, bitRate, nChannels, true, false )
    val sourceDataLine = AudioSystem.getSourceDataLine( audioFormat )
    sourceDataLine.open();
    sourceDataLine.start();
    val aFreqsWObertones = Seq(Seq((220-2) to (220+2)), Seq((440-2) to (440+2)), Seq((110-2) to (110 + 2)), Seq((55 - 2) to (55 + 2))).flatten.flatten
    val aFreqs = Seq(220, 440, 110, 55)
    val eFreqs = Seq(41, 82, 164, 328)
    (0 until seconds * sampleRate.toInt) foreach { t =>
      val a16 = addFrequencies(t, aFreqs)
      buf(0) = (a16 % 256).toByte
      buf(1) = (a16 / 256).toByte
      sourceDataLine.write( buf, 0, 2 );
    }
    sourceDataLine.drain();
    sourceDataLine.stop();
    
    def addFrequencies(t: Int, frequencies: Seq[Int]) = {
      frequencies.foldLeft(0.0) { (sumA, f) =>
        val angle = 2.0 * Math.PI * f * t / sampleRate
        sumA + ( Math.sin( angle ) * amplitude )
      }.toShort
    }
}