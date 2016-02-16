definition(
    name: "Zigbee Bulb Wake Up",
    namespace: "sticks18",
    author: "sgibson18@gmail.com",
    description: "Use a momentary switch to trigger a slow fade up of zigbee bulbs over selected timeframe",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience%402x.png"
)

preferences {
	section("Select virtual momentary switch to use as trigger"){
		input "trigger", "capability.momentary", multiple: false, required: true
	}
    section("Slowly turn on these zigbee bulbs..."){
		input "bulbs", "capability.switch", multiple: true, required: true
	}
    section("Turn on to this brightness (0-100)..."){
    	input "lightLevel", "number", required: true
    }
    section("Fade up over this many minutes..."){
    	input "fadeTime", "number", required: true
    }
}

def installed()
{
    initialize()
}

def updated()
{
	unsubscribe()
    initialize()
}


def wakeHandler(evt) {
	log.info evt.value
    def tranTime = fadeTime * 60
    def level = Math.min(lightLevel, 100)
    bulbs.setLevel(0,0) + bulbs.setLevel(level, tranTime)
}

def initialize() {
	subscribe(trigger, "momentary.pushed", wakeHandler)
}
