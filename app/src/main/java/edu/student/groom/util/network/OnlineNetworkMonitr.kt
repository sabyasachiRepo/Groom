package edu.student.groom.util.network

import kotlinx.coroutines.flow.Flow

class OnlineNetworkMonitor(override val isOnline: Flow<Boolean>) :NetworkMonitor{
}