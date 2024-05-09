package com.uobfintech.cyberpets.repository;

import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.DecoderContext;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeCodec implements Codec<ZonedDateTime> {

    @Override
    public void encode(final BsonWriter writer, final ZonedDateTime value, final EncoderContext encoderContext) {
        writer.writeString(value.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }

    @Override
    public ZonedDateTime decode(final BsonReader reader, final DecoderContext decoderContext) {
        return ZonedDateTime.parse(reader.readString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    @Override
    public Class<ZonedDateTime> getEncoderClass() {
        return ZonedDateTime.class;
    }
}

