package com.example.composeexperiments.ui.components.shaders

import android.graphics.RuntimeShader
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language


@Composable
// Currently out of commission, because
// the performance wasn't great
fun WaveShader(color: Color, modifier: Modifier) {
    Box(modifier = modifier.then(WaveElement(color)))
}

private data class WaveElement(val color: Color) : ModifierNodeElement<WaveNode>() {
    override fun create() = WaveNode(color)

    override fun update(node: WaveNode) {}
}

private class WaveNode(color: Color) : DrawModifierNode, Modifier.Node() {
    private val shader = RuntimeShader(SHADER)
    private val shaderBrush = ShaderBrush(shader)
    private val time = mutableFloatStateOf(0f)

    init {
        shader.setColorUniform(
            "color",
            android.graphics.Color.valueOf(
                color.red, color.green, color.blue, color.alpha
            )
        )
    }

    override fun ContentDrawScope.draw() {
        shader.setFloatUniform("resolution", size.width, size.height)
        shader.setFloatUniform("time", time.floatValue)
        drawRect(shaderBrush)
        drawContent()
    }

    override fun onAttach() {
        coroutineScope.launch {
            while (true) {
                withInfiniteAnimationFrameMillis {
                    time.floatValue = it / 1000f
                }
            }
        }
    }
}

@Language("AGSL")
private val SHADER = """
    uniform float2 resolution;
    uniform float time;
    layout(color) uniform half4 color;
    
    float calculateColorMultiplier(float yCoord, float factor) {
        return step(yCoord, 1.0 + factor * 2.0) - step(yCoord, factor - 0.1);
    }

    float4 main(in float2 fragCoord) {
        // Config values
        const float speedMultiplier = 1.5;
        const float waveDensity = 1.0;
        const float loops = 8.0;
        const float energy = 0.6;
        
        // Calculated values
        float2 uv = fragCoord / resolution.xy;
        float3 rgbColor = color.rgb;
        float timeOffset = time * speedMultiplier;
        float hAdjustment = uv.x * 4.3;
        float3 loopColor = vec3(1.0 - rgbColor.r, 1.0 - rgbColor.g, 1.0 - rgbColor.b) / loops;
        
        for (float i = 1.0; i <= loops; i += 1.0) {
            float loopFactor = i * 0.1;
            float sinInput = (timeOffset + hAdjustment) * energy;
            float curve = sin(sinInput) * (1.0 - loopFactor) * 0.05;
            float colorMultiplier = calculateColorMultiplier(uv.y, loopFactor);
            rgbColor += loopColor * colorMultiplier;
            
            // Offset for next loop
            uv.y += curve;
        }
        
        return float4(rgbColor, 1.0);
    }
""".trimIndent()
