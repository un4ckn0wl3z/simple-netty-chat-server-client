package org.example.org.example.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;


public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<String> {

    //private static final String name = "NEWTUN";
    private static final ChannelGroup channels = new DefaultChannelGroup();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel: channels){
                channel.write("[SERVER] - " + incoming.remoteAddress() + " has joined!");
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel: channels){
            channel.write("[SERVER] - " + incoming.remoteAddress() + " has left!");
        }
        channels.remove(ctx.channel());
    }

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel: channels){
            if(channel != incoming){
                channel.write("[ "+incoming.remoteAddress()+" ]" + ": " + s + " : \n");
            }
        }
    }
}