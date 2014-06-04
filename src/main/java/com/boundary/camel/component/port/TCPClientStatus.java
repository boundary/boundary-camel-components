package com.boundary.camel.component.port;

public enum TCPClientStatus {
	CONNECTED,
	CONNECTION_REFUSED,
	ERROR,
	SOCKET_TIMEOUT,
	UNKNOWN_HOST
}