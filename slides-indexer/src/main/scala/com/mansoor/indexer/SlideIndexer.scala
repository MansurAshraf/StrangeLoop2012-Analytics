package com.mansoor.indexer

import java.io.{FileOutputStream, File}
import org.apache.lucene.util.Version
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.store.SimpleFSDirectory
import org.apache.lucene.index.{IndexWriterConfig, IndexWriter}
import org.apache.lucene.document.{Field, Document}
import org.apache.lucene.document.Field.{TermVector, Index, Store}
import org.apache.tika.Tika
import collection.JavaConversions._
import java.net.URL
import org.apache.commons.compress.utils.IOUtils
import org.eclipse.jgit.api.{CloneCommand, GitCommand}

/**
 *
 * @author Muhammad Ashraf
 * @since 11/4/12
 *
 */
object SlideIndexer {
  final val SLIDES_DIR = "/strangeloop2012/slides"
  final val ELC = SLIDES_DIR + "/elc"
  final val SESSIONS = SLIDES_DIR + "/sessions"
  final val WORKSHOPS = SLIDES_DIR + "/unsessions"
  final val UNSESSIONS = SLIDES_DIR + "/workshops"
  final val OUTPUT_DIR = System.getProperty("java.io.tmpdir")+"/lucene-index"

  def main(args: Array[String]) {
    val slides = getSlides
    val outputDir = getOutputDir
    index(slides,outputDir)
  }


  def index(slides: List[File], outputDir: File) {

    val config = new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36, StopWords.stopwordSet))
    val indexWriter = new IndexWriter(new SimpleFSDirectory(outputDir), config)
    indexWriter.setMaxFieldLength(Integer.MAX_VALUE)
    val tika = new Tika()

    slides.foldLeft(indexWriter) {
      case (writer, file) => val document = new Document
      println("parsing " + file.getName)
      document.add(new Field("filename", file.getName, Store.YES, Index.ANALYZED, TermVector.YES))
      document.add(new Field("content", tika.parseToString(file), Store.NO, Index.ANALYZED, TermVector.YES))
      writer.addDocument(document)
      writer
    }.close()
  }

  def getOutputDir = {
    val outputDir = new File(OUTPUT_DIR)
    if (outputDir.exists()) outputDir.delete()
    outputDir
  }

  def getSlides: List[File] = {
    assert(new File(this.getClass.getResource(SLIDES_DIR).getFile).exists(), "Slides directory does not exsit! Did you clone the submodule?")

    val elc = new File(this.getClass.getResource(ELC).getFile)
    val sessions = new File(this.getClass.getResource(SESSIONS).getFile)
    val workshops = new File(this.getClass.getResource(WORKSHOPS).getFile)
    val unsessions = new File(this.getClass.getResource(UNSESSIONS).getFile)
    List(elc, sessions, unsessions, workshops).
      map(dir =>
      dir.listFiles()).
      flatten
  }
}
