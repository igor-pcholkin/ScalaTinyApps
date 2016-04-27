package sound

import javax.sound.midi.MidiChannel
import javax.sound.midi.MidiSystem
import java.util.Calendar
import scala.util.Random

/**
 * Scala app using Java MIDI API to play a song in several voices simultanously.
 * With main instrument randomly changing while playing. 
 */
object MidiPlay extends App {
  val synth = MidiSystem.getSynthesizer();
  synth.open();
  val channels = synth.getChannels();

  val Array(c1, c$1, d1, d$1, e1, f1, f$1, g1, g$1, a1, a$1, b1, c2, c$2, d2, d$2, e2, f2, f$2, g2, g$2, a2) = 60 to 81 toArray 
  
  val Array(ab, a$b, bb, cb, c$b, db, d$b, eb, fb, f$b, gb, g$b) = 33 to 44 toArray
  
  val verse = Seq((f$1, 1, 5), (a1, 1, 6), (c$2, 1, 7), (d2, 13, 8), 
      (e1, 1, 5), (a1, 1, 6), (b1, 1, 7), (c$2, 3, 8), (d2, 1, 7), (c$2, 2, 8), (b1, 2, 7), (a1, 5, 6),
      (d1, 1, 5), (f$1, 1, 6), (a1, 1, 7), (b1, 9, 8),
      (c$2, 2, 9), (b1, 1, 8), (b1, 5, 8), (c$2, 2, 10), (b1, 1, 8))
  
  val chorus = Seq((c$2, 1, 8), (c$2, 1, 8), (c$2, 2, 7), (d2, 4, 8),
      (d2, 1, 8), (c$2, 1, 7), (b1, 2, 6), (b1, 4, 7),
      (b1, 1, 6), (c$2, 1, 7), (d2, 1, 8), (c$2, 3, 7), (b1, 1, 6), (b1, 3, 7), (g$1, 1, 6))
  
  val chorus2 = Seq((c$2, 1, 6), (c$2, 1, 7), (c$2, 2, 7), (d2, 4, 8),
      (d2, 1, 8), (c$2, 1, 7), (b1, 2, 6), (b1, 4, 7),
      (b1, 1, 6), (c$2, 1, 7), (d2, 1, 8), (e2, 3, 7), (d2, 1, 6))    
      
  val melody = Seq(
      n((0, 4, 0),1), 
      verse, n((b1, 9, 8),1), verse, n((b1, 2, 8),1),
      n((c$2, 7, 9),1),
      chorus, n((a1, 5, 6), 1), chorus2, n((c$2, 3, 6), 1), n((b1, 1, 6), 1), n((a1, 5, 6), 1),
      chorus, n((b1, 2, 7), 1), n((a1, 3, 6), 1), chorus2, n((c$2, 2, 7), 1), n((b1, 1, 6), 1), n((a1, 1, 5), 1), n((c$2, 17, 7), 1)
  ).flatten
  
  val bass = Array(
      ns(2, n(f$b, 3), n(c$b, 1), n(f$b, 3), n(c$b, 1), n(bb, 3), n(f$b, 1), n(bb, 3), n(ab, 1),
      n(eb, 3), n(bb, 1), n(eb, 3), n(bb, 1), n(ab, 3), n(eb, 1), n(ab, 4), 
      n(db, 8), n(d$b, 8),
      n(eb, 8), n(fb, 8)), 
      ns(4, n(f$b, 8), n(bb, 8), n(eb, 8), n(fb, 8)),
      n(fb, 18)
  ).flatten

  val second = Seq(
      ns(64, n(b1, 3), n(a1, 1)),
      n(0, 128)
  ).flatten

  val third = Array(
      n(0, 128),
      ns(4, n(a1, 1), n(c$2, 1), n(f$1, 1), n(c$2, 1),
      n(a1, 1), n(c$2, 1), n(f$1, 1), n(c$2, 1),
      n(b1, 1), n(d2, 1), n(f$1, 1), n(d2, 1),
      n(b1, 1), n(d2, 1), n(f$1, 1), n(d2, 1),
      n(g$1, 1), n(b1, 1), n(e1, 1), n(b1, 1),
      n(g$1, 1), n(b1, 1), n(e1, 1), n(b1, 1),
      n(g$1, 1), n(b1, 1), n(f1, 1), n(b1, 1),
      n(b1, 1), n(a1, 1), n(g$1, 1), n(a1, 1)),
      n(0, 128)
  ).flatten
  
  def n[T](notes: T, times: Int) = (0 until times).map(i => notes)
  def ns[T](times: Int, notes: Seq[T]*) = (0 until times).map(i => notes.flatten).flatten
  
  channels(0).programChange(9)
  channels(1).programChange(35)
  channels(2).programChange(46)
  channels(3).programChange(7)
  channels(4).programChange(50)
  
  (1 to 2).map { verse =>
    melody.foldLeft(0) {
      case (tick, (note, duration, volume)) =>
        playNote(tick, verse, note, duration, volume)
    }
  }
  
  synth.close()
  
  def playNote(i: Int, verse: Int, note: Int, duration: Int, volume: Int) = {
    if (i % 16 == 0) { 
      channels(0).programChange(Math.abs(Random.nextInt()) % 128)
    }
    channels(0).noteOn( note, volume * 20)
    channels(4).noteOn( note, volume * 5)
    val tick = sleepNAccompany(duration, i, verse)
    channels(0).noteOff( note, 1 )
    channels(4).noteOff( note, 1 )
    tick
  }
 
  def sleepNAccompany(d: Int, tick: Int, verse:Int): Int = {
    accOn(tick, verse)
    
    Thread.sleep(300)
    
    accOff(tick, verse)
    
    val nextTick = tick + 1
    if (d > 1)
      sleepNAccompany(d - 1, nextTick, verse)
    else  
      nextTick  
  }
  
  def accOn(tick: Int, verse: Int) = {
    channels(1).noteOn( bass(tick), 80 )
    channels(2).noteOn( second(tick), 40 )
    if (verse == 2) channels(3).noteOn( third(tick), 80 )
  }

  def accOff(tick: Int, verse: Int) = {
    channels(1).noteOff( bass(tick), 1 )
    channels(2).noteOff( second(tick), 1 )
    if (verse == 2) channels(3).noteOff( third(tick), 1 )
  }
}