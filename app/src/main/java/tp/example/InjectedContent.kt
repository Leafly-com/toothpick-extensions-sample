package tp.example

import javax.inject.Inject

class InjectedContent @Inject constructor() : InjectContentInterface {
    override val content = "Injected Content!"
}
