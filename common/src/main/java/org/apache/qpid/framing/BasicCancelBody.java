/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

/*
 * This file is auto-generated by Qpid Gentools v.0.1 - do not modify.
 * Supported AMQP version:
 *   8-0
 */

package org.apache.qpid.framing;

import java.io.DataOutput;
import java.io.IOException;

import org.apache.qpid.QpidException;
import org.apache.qpid.codec.MarkableDataInput;

public class BasicCancelBody extends AMQMethodBodyImpl implements EncodableAMQDataBlock, AMQMethodBody
{

    public static final int CLASS_ID =  60;
    public static final int METHOD_ID = 30;

    // Fields declared in specification
    private final AMQShortString _consumerTag; // [consumerTag]
    private final byte _bitfield0; // [nowait]

    // Constructor
    public BasicCancelBody(MarkableDataInput buffer) throws AMQFrameDecodingException, IOException
    {
        _consumerTag = buffer.readAMQShortString();
        _bitfield0 = buffer.readByte();
    }

    public BasicCancelBody(
            AMQShortString consumerTag,
            boolean nowait
                          )
    {
        _consumerTag = consumerTag;
        byte bitfield0 = (byte)0;
        if( nowait )
        {
            bitfield0 = (byte) (((int) bitfield0) | (1 << 0));
        }
        _bitfield0 = bitfield0;
    }

    public int getClazz()
    {
        return CLASS_ID;
    }

    public int getMethod()
    {
        return METHOD_ID;
    }

    public final AMQShortString getConsumerTag()
    {
        return _consumerTag;
    }
    public final boolean getNowait()
    {
        return (((int)(_bitfield0)) & ( 1 << 0)) != 0;
    }

    protected int getBodySize()
    {
        int size = 1;
        size += getSizeOf( _consumerTag );
        return size;
    }

    public void writeMethodPayload(DataOutput buffer) throws IOException
    {
        writeAMQShortString( buffer, _consumerTag );
        writeBitfield( buffer, _bitfield0 );
    }

    public boolean execute(MethodDispatcher dispatcher, int channelId) throws QpidException
	{
        return dispatcher.dispatchBasicCancel(this, channelId);
	}

    public String toString()
    {
        StringBuilder buf = new StringBuilder("[BasicCancelBodyImpl: ");
        buf.append( "consumerTag=" );
        buf.append(  getConsumerTag() );
        buf.append( ", " );
        buf.append( "nowait=" );
        buf.append(  getNowait() );
        buf.append("]");
        return buf.toString();
    }

    public static void process(final MarkableDataInput buffer,
                               final ServerChannelMethodProcessor dispatcher) throws IOException
    {

        AMQShortString consumerTag = buffer.readAMQShortString();
        boolean noWait = (buffer.readByte() & 0x01) == 0x01;
        if(!dispatcher.ignoreAllButCloseOk())
        {
            dispatcher.receiveBasicCancel(consumerTag, noWait);
        }
    }
}
