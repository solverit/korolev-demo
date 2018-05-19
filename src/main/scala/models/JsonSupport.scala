package models

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.model.{ContentTypeRange, MediaType}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import akka.util.ByteString
import upickle.default.{Reader, Writer, macroRW, readJs, writeJs, ReadWriter => RW}
import upickle.{Js, json}

import scala.collection.immutable.Seq


/**
  * Automatic to and from JSON marshalling/unmarshalling using an in-scope *play-json* protocol.
  */
trait JsonSupport {

  implicit def errorMessageFormat: RW[ErrorMessage] = macroRW
  implicit def applicationStatusFormat: RW[ApplicationStatus] = macroRW
  implicit def applicationInformationFormat: RW[ApplicationInformation] = macroRW

  def unmarshallerContentTypes: Seq[ContentTypeRange] = {
    List(`application/json`)
  }

  def mediaTypes: Seq[MediaType.WithFixedCharset] =
    List(`application/json`)

  private val jsonStringUnmarshaller =
    Unmarshaller.byteStringUnmarshaller
      .forContentTypes(unmarshallerContentTypes: _*)
      .mapWithCharset {
        case (ByteString.empty, _) => throw Unmarshaller.NoContentException
        case (data, charset)       => data.decodeString(charset.nioCharset.name)
      }

  private val jsonStringMarshaller =
    Marshaller.oneOf(mediaTypes: _*)(Marshaller.stringMarshaller)

  /**
    * HTTP entity => `A`
    *
    * @tparam A type to decode
    * @return unmarshaller for `A`
    */
  implicit def unmarshaller[A: Reader]: FromEntityUnmarshaller[A] =
    jsonStringUnmarshaller.map(data => readJs[A](json.read(data)))

  /**
    * `A` => HTTP entity
    *
    * @tparam A type to encode
    * @return marshaller for any `A` value
    */
  implicit def marshaller[A: Writer]: ToEntityMarshaller[A] =
    jsonStringMarshaller.compose(json.write(_: Js.Value, 0)).compose(writeJs[A])

}