package boundary.com.camel.component.ping;

/**
 * 
 * @author davidg
 *
 *     The ping utility uses the ICMP protocol's mandatory ECHO_REQUEST datagram to elicit an ICMP ECHO_RESPONSE from a host or gateway.  ECHO_REQUEST datagrams
     (``pings'') have an IP and ICMP header, followed by a ``struct timeval'' and then an arbitrary number of ``pad'' bytes used to fill out the packet.  The
     options are as follows:

     -A      Audible.  Output a bell (ASCII 0x07) character when no packet is received before the next packet is transmitted.  To cater for round-trip times
             that are longer than the interval between transmissions, further missing packets cause a bell only if the maximum number of unreceived packets
             has increased.

     -a      Audible.  Include a bell (ASCII 0x07) character in the output when any packet is received.  This option is ignored if other format options are
             present.

     -b boundif
             Bind the socket to interface boundif for sending.

     -C      Prohibit the socket from using the cellular network interface.

     -c count
             Stop after sending (and receiving) count ECHO_RESPONSE packets.  If this option is not specified, ping will operate until interrupted.  If this
             option is specified in conjunction with ping sweeps, each sweep will consist of count packets.

     -D      Set the Don't Fragment bit.

     -d      Set the SO_DEBUG option on the socket being used.

     -f      Flood ping.  Outputs packets as fast as they come back or one hundred times per second, whichever is more.  For every ECHO_REQUEST sent a period
             ``.'' is printed, while for every ECHO_REPLY received a backspace is printed.  This provides a rapid display of how many packets are being
             dropped.  Only the super-user may use this option.  This can be very hard on a network and should be used with caution.

     -G sweepmaxsize
             Specify the maximum size of ICMP payload when sending sweeping pings.  This option is required for ping sweeps.

     -g sweepminsize
             Specify the size of ICMP payload to start with when sending sweeping pings.  The default value is 0.

     -h sweepincrsize
     
          -I iface
             Source multicast packets with the given interface address.  This flag only applies if the ping destination is a multicast address.

     -i wait
             Wait wait seconds between sending each packet.  The default is to wait for one second between each packet.  The wait time may be fractional, but
             only the super-user may specify values less than 0.1 second.  This option is incompatible with the -f option.

     -L      Suppress loopback of multicast packets.  This flag only applies if the ping destination is a multicast address.

     -l preload
             If preload is specified, ping sends that many packets as fast as possible before falling into its normal mode of behavior.  Only the super-user
             may use this option.

     -M mask | time
             Use ICMP_MASKREQ or ICMP_TSTAMP instead of ICMP_ECHO.  For mask, print the netmask of the remote machine.  Set the net.inet.icmp.maskrepl MIB
             variable to enable ICMP_MASKREPLY.  For time, print the origination, reception and transmission timestamps.

     -m ttl  Set the IP Time To Live for outgoing packets.  If not specified, the kernel uses the value of the net.inet.ip.ttl MIB variable.

     -n      Numeric output only.  No attempt will be made to lookup symbolic names for host addresses.

     -o      Exit successfully after receiving one reply packet.

     -P policy
             policy specifies IPsec policy for the ping session.  For details please refer to ipsec(4) and ipsec_set_policy(3).

     -p pattern
             You may specify up to 16 ``pad'' bytes to fill out the packet you send.  This is useful for diagnosing data-dependent problems in a network.  For
             example, ``-p ff'' will cause the sent packet to be filled with all ones.

     -Q      Somewhat quiet output.  Don't display ICMP error messages that are in response to our query messages.  Originally, the -v flag was required to
             display such errors, but -v displays all ICMP error messages.  On a busy machine, this output can be overbearing.  Without the -Q flag, ping
             prints out any ICMP error messages caused by its own ECHO_REQUEST messages.

     -q      Quiet output.  Nothing is displayed except the summary lines at startup time and when finished.

     -R      Record route.  Includes the RECORD_ROUTE option in the ECHO_REQUEST packet and displays the route buffer on returned packets.  Note that the IP
             header is only large enough for nine such routes; the traceroute(8) command is usually better at determining the route packets take to a particu-
             lar destination.  If more routes come back than should, such as due to an illegal spoofed packet, ping will print the route list and then trun-
             cate it at the correct spot.  Many hosts ignore or discard the RECORD_ROUTE option.

     -r      Bypass the normal routing tables and send directly to a host on an attached network.  If the host is not on a directly-attached network, an error
             is returned.  This option can be used to ping a local host through an interface that has no route through it (e.g., after the interface was
             dropped by routed(8)).

     -S src_addr
     
                  Use the following IP address as the source address in outgoing packets.  On hosts with more than one IP address, this option can be used to force
             the source address to be something other than the IP address of the interface the probe packet is sent on.  If the IP address is not one of this
             machine's interface addresses, an error is returned and nothing is sent.

     -s packetsize
             Specify the number of data bytes to be sent.  The default is 56, which translates into 64 ICMP data bytes when combined with the 8 bytes of ICMP
             header data.  This option cannot be used with ping sweeps.

     -T ttl  Set the IP Time To Live for multicasted packets.  This flag only applies if the ping destination is a multicast address.

     -t timeout
             Specify a timeout, in seconds, before ping exits regardless of how many packets have been received.

     -v      Verbose output.  ICMP packets other than ECHO_RESPONSE that are received are listed.

     -W waittime
             Time in milliseconds to wait for a reply for each packet sent.  If a reply arrives later, the packet is not printed as replied, but considered as
             replied when calculating statistics.

     -z tos  Use the specified type of service.



 */
public class PingArguments {
	
	private final long DEFAULT_PACKET_SIZE = 0;

	public final long DEFAULT_WAIT_TIME = 5000;
	
	private long waitTime;

	private long packetSize;
	
	public PingArguments() {
		this.waitTime = DEFAULT_WAIT_TIME;
		this.packetSize = DEFAULT_PACKET_SIZE;
	}

	/**
	 * 
	 * @param waitTime
	 */
	void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public long getWaitTime() {
		return waitTime;
	}


	public void setPacketSize(long packetSize) {
		this.packetSize = packetSize;
	}

	public long getPacketSize() {
		return this.packetSize;
	}

}
