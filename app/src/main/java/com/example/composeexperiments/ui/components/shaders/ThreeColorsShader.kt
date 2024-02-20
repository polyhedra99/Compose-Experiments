package com.example.composeexperiments.ui.components.shaders

import android.graphics.RuntimeShader
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import org.intellij.lang.annotations.Language

@Composable
fun ThreeColorsShader(
    color1: Color,
    color2: Color,
    color3: Color,
    modifier: Modifier
) {
    Box(modifier = modifier.then(ThreeColorsElement(color1, color2, color3)))
}

private data class ThreeColorsElement(
    val color1: Color,
    val color2: Color,
    val color3: Color
) : ModifierNodeElement<ThreeColorsNode>() {
    override fun create() = ThreeColorsNode(color1, color2, color3)

    override fun update(node: ThreeColorsNode) {}
}

private class ThreeColorsNode(
    color1: Color,
    color2: Color,
    color3: Color
) : DrawModifierNode, Modifier.Node() {
    private val shader = RuntimeShader(SHADER)
    private val shaderBrush = ShaderBrush(shader)

    init {
        shader.setColorUniform(
            "color1",
            android.graphics.Color.valueOf(
                color1.red, color1.green, color1.blue, color1.alpha
            )
        )
        shader.setColorUniform(
            "color2",
            android.graphics.Color.valueOf(
                color2.red, color2.green, color2.blue, color2.alpha
            )
        )
        shader.setColorUniform(
            "color3",
            android.graphics.Color.valueOf(
                color3.red, color3.green, color3.blue, color3.alpha
            )
        )
    }

    override fun ContentDrawScope.draw() {
        shader.setFloatUniform("resolution", size.width, size.height)
        drawRect(shaderBrush)
        drawContent()
    }
}

@Language("AGSL")
private val SHADER = """
    uniform float2 resolution;
    layout(color) uniform half4 color1;
    layout(color) uniform half4 color2;
    layout(color) uniform half4 color3;
    
    half4 main(in float2 fragCoord) {
        float2 verticalUv = vec2(0, (fragCoord/resolution).y);
        float verticalMixValue = distance(verticalUv, vec2(0, 1));
        float4 verticalMix = mix(color1, color2, verticalMixValue);
        
        float2 horizontalUv = vec2((fragCoord/resolution).x, 1);
        float horizontalMixValue = distance(horizontalUv, vec2(0.3, 0.8));
        float4 horizontalMix = mix(verticalMix, color3, horizontalMixValue);
        return horizontalMix;
    }
""".trimIndent()