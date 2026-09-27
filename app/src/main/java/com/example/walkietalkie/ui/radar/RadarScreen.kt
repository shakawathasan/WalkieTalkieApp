package com.example.walkietalkie.ui.radar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.walkietalkie.WalkieTalkieApp
import com.example.walkietalkie.domain.model.ConnectionQuality
import com.example.walkietalkie.domain.model.NearbyPeer
import kotlin.math.cos
import kotlin.math.sin

/**
 * Draws real connected/discovered peers around "YOU". Angle is arbitrary
 * (evenly spaced) since we deliberately do NOT claim to know physical
 * direction or exact distance (spec section 14: no fake GPS distance).
 * Radius reflects hop count / quality only.
 */
@Composable
fun RadarScreen(app: WalkieTalkieApp) {
    val peers by app.nearbyManager.peers.collectAsState(initial = emptyMap())
    val peerList = peers.values.toList()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Connection Radar", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Box(Modifier.fillMaxWidth().weight(1f)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                drawCircle(Color.LightGray, radius = size.minDimension / 2, center = center, style = Stroke(2f))
                drawCircle(Color.LightGray, radius = size.minDimension / 3, center = center, style = Stroke(2f))

                peerList.forEachIndexed { index, peer ->
                    val angle = (2 * Math.PI / maxOf(peerList.size, 1)) * index
                    val radiusFraction = when (peer.quality) {
                        ConnectionQuality.STRONG -> 0.3f
                        ConnectionQuality.MEDIUM -> 0.5f
                        ConnectionQuality.WEAK -> 0.75f
                        ConnectionQuality.LOST -> 0.95f
                    }
                    val r = (size.minDimension / 2) * radiusFraction
                    val point = Offset(
                        center.x + (r * cos(angle)).toFloat(),
                        center.y + (r * sin(angle)).toFloat()
                    )
                    drawLine(qualityColor(peer.quality), center, point, strokeWidth = 3f)
                    drawCircle(qualityColor(peer.quality), radius = 14f, center = point)
                }
                drawCircle(Color.Black, radius = 18f, center = center)
            }
            Text("YOU", Modifier.align(Alignment.Center))
        }
        peerList.forEach { peer ->
            Text("${peer.displayName} · ${peer.quality.name} · ${if (peer.isDirect) "Direct" else peer.hopCount.toString() + " hop(s)"}")
        }
    }
}

private fun qualityColor(quality: ConnectionQuality): Color = when (quality) {
    ConnectionQuality.STRONG -> Color(0xFF2E7D32)
    ConnectionQuality.MEDIUM -> Color(0xFFF9A825)
    ConnectionQuality.WEAK -> Color(0xFFEF6C00)
    ConnectionQuality.LOST -> Color(0xFFC62828)
}
