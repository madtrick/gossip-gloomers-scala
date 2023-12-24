package com.github.madtrick.glomers

object Main extends App {
  var msgCounter = 0

  for (line <- io.Source.stdin.getLines()) {
    val json = ujson.read(line)

    val src     = json("src").str
    val dest    = json("dest").str
    val msgType = json("body")("type").str
    val msgId   = json("body")("msg_id").num

    msgCounter += 1

    msgType match {
      case "init" => {
        println(
          ujson.write(
            ujson.Obj(
              "src"  -> dest,
              "dest" -> src,
              "body" -> ujson.Obj(
                "type"        -> "init_ok",
                "in_reply_to" -> msgId,
                "msg_id"      -> msgCounter
              )
            )
          )
        )
      }
      case "echo" => {
        val echo = json("body")("echo").str

        println(
          ujson.write(
            ujson.Obj(
              "src"  -> dest,
              "dest" -> src,
              "body" -> ujson.Obj(
                "type"        -> "echo_ok",
                "msg_id"      -> msgCounter,
                "in_reply_to" -> msgId,
                "echo"        -> echo
              )
            )
          )
        )
      }
    }

  }
}
