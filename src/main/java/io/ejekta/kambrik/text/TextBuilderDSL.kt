package io.ejekta.kambrik.text

import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.text.*
import net.minecraft.util.Formatting
import java.util.*


fun textLiteral(str: String = "", func: KambrikTextBuilder<LiteralText>.() -> Unit = {}): LiteralText {
    return textBuilder(LiteralText(str), func)
}

fun textTranslate(key: String, func: KambrikTextBuilder<TranslatableText>.() -> Unit = {}): TranslatableText {
    return textBuilder(TranslatableText(key), func)
}

fun textKeybind(key: String, func: KambrikTextBuilder<KeybindText>.() -> Unit = {}): KeybindText {
    return textBuilder(KeybindText(key), func)
}

fun textScore(name: String, objective: String, func: KambrikTextBuilder<ScoreText>.() -> Unit = {}): ScoreText {
    return textBuilder(ScoreText(name, objective), func)
}

fun textSelector(pattern: String, separator: Text?, func: KambrikTextBuilder<SelectorText>.() -> Unit = {}): SelectorText {
    return textBuilder(SelectorText(pattern, Optional.ofNullable(separator)), func)
}

internal fun <T : BaseText> textBuilder(starterText: T, func: KambrikTextBuilder<T>.() -> Unit): T {
    val builder = KambrikTextBuilder(starterText)
    builder.func()
    return builder.root
}

class KambrikTextBuilder<T : BaseText>(
    var root: T
) {

    fun format(vararg formats: Formatting) {
        root.formatted(*formats)
    }

    fun color(color: Int) {
        root.style = root.style.withColor(TextColor.fromRgb(color))
    }

    var bold: Boolean
        get() = root.style.isBold
        set(value) {
            root.style = root.style.withBold(value)
        }

    var italics: Boolean
        get() = root.style.isItalic
        set(value) {
            root.style = root.style.withItalic(value)
        }

    var strikeThrough: Boolean
        get() = root.style.isStrikethrough
        set(value) {
            root.style = root.style.withStrikethrough(value)
        }

    var obfuscated: Boolean
        get() = root.style.isObfuscated
        set(value) {
            root.style = root.style.withObfuscated(value)
        }

    var color: Int
        get() = root.style.color?.rgb ?: 0x000000
        set(value) {
            color(value)
        }

    var clickEvent: ClickEvent?
        get() = root.style.clickEvent
        set(value) {
            root.style = root.style.withClickEvent(value)
        }

    var hoverEvent: HoverEvent?
        get() = root.style.hoverEvent
        set(value) {
            root.style = root.style.withHoverEvent(value)
        }

    fun onHoverShowItem(itemStack: ItemStack) {
        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_ITEM, HoverEvent.ItemStackContent(itemStack))
    }

    fun onHoverShowText(text: Text) {
        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, text)
    }

    fun onHoverShowText(inFunc: KambrikTextBuilder<LiteralText>.() -> Unit) {
        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, textLiteral("", inFunc))
    }

    fun onHoverShowEntity(entity: Entity) {
        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_ENTITY, HoverEvent.EntityContent(entity.type, entity.uuid, entity.name))
    }

    fun newLine() = addLiteral("\n")

    operator fun T.invoke(inFunc: KambrikTextBuilder<T>.() -> Unit): KambrikTextBuilder<T> {
        return KambrikTextBuilder(this).apply(inFunc)
    }

    operator fun String.invoke(inFunc: KambrikTextBuilder<LiteralText>.() -> Unit): LiteralText {
        return KambrikTextBuilder(LiteralText(this)).apply(inFunc).root
    }

    fun add(text: Text) {
        root.append(text)
    }

    fun addLiteral(str: String, func: KambrikTextBuilder<LiteralText>.() -> Unit = {}) {
        root.append(textLiteral(str, func))
    }

    fun addTranslate(key: String, func: KambrikTextBuilder<TranslatableText>.() -> Unit = {}) {
        root.append(textTranslate(key, func))
    }

}


fun main() {



    val test = textLiteral("Hello ") {
        format(Formatting.GOLD, Formatting.ITALIC)
        addLiteral("Joe") {
            color = 0x55ff33
            bold = true
            italics = true
            strikeThrough = false
            obfuscated = false
            format(Formatting.AQUA)
        }
        addLiteral(", how are you?")
    }

    /*
    val text = LiteralText("Hello ")
        .formatted(Formatting.GOLD, Formatting.ITALIC)
        .append(
            LiteralText(player.displayName)
                .formatted(Formatting.AQUA)
        )
        .append(LiteralText(", how are you?"))

     */

    println(test)
    println(test.string)

}
