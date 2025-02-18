package io.ejekta.kambrik.ext.render

import net.minecraft.client.render.VertexConsumer
import net.minecraft.util.math.Matrix4f
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f

inline fun VertexConsumer.vertex(matrix4f: Matrix4f, vec3d: Vec3d): VertexConsumer {
    return apply {
        vertex(matrix4f, vec3d.x.toFloat(), vec3d.y.toFloat(), vec3d.z.toFloat())
    }
}

inline fun VertexConsumer.vertex(matrix4f: Matrix4f, vec3f: Vec3f): VertexConsumer {
    return apply {
        vertex(matrix4f, vec3f.x, vec3f.y, vec3f.z)
    }
}