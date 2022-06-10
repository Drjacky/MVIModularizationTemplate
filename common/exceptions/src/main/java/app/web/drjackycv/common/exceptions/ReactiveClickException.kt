package app.web.drjackycv.common.exceptions

class ReactiveClickException(
    msg: String,
    cause: Throwable?,
    stack: Array<StackTraceElement>
) : Throwable("$msg ::: ${stack.joinToString()}", cause, true, true)