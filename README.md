nohup: ignoring input
16:33:36,615 |-INFO in ch.qos.logback.classic.LoggerContext[natcross2] - Could NOT find resource [logback-test.xml]
16:33:36,615 |-INFO in ch.qos.logback.classic.LoggerContext[natcross2] - Could NOT find resource [logback.groovy]
16:33:36,615 |-INFO in ch.qos.logback.classic.LoggerContext[natcross2] - Found resource [logback.xml] at [jar:file:/home/natcross/natcross2/ServerApp.jar!/logback.xml]
16:33:36,626 |-INFO in ch.qos.logback.core.joran.spi.ConfigurationWatchList@7bfcd12c - URL [jar:file:/home/natcross/natcross2/ServerApp.jar!/logback.xml] is not of type file
16:33:36,665 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - debug attribute not set
16:33:36,671 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - Will scan for changes in [jar:file:/home/natcross/natcross2/ServerApp.jar!/logback.xml] 
16:33:36,671 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - Setting ReconfigureOnChangeTask scanning period to 1 minutes
16:33:36,676 |-ERROR in ch.qos.logback.core.joran.spi.Interpreter@6:107 - no applicable action for [springProperty], current ElementPath  is [[configuration][springProperty]]
16:33:36,676 |-ERROR in ch.qos.logback.core.joran.spi.Interpreter@9:88 - no applicable action for [springProperty], current ElementPath  is [[configuration][springProperty]]
16:33:36,676 |-ERROR in ch.qos.logback.core.joran.spi.Interpreter@10:105 - no applicable action for [springProperty], current ElementPath  is [[configuration][springProperty]]
16:33:36,676 |-INFO in ch.qos.logback.classic.joran.action.ContextNameAction - Setting logger context name as [natcross2]
16:33:36,676 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.core.ConsoleAppender]
16:33:36,678 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [consoleLog]
16:33:36,714 |-WARN in ch.qos.logback.core.ConsoleAppender[consoleLog] - This appender no longer admits a layout as a sub-component, set an encoder instead.
16:33:36,714 |-WARN in ch.qos.logback.core.ConsoleAppender[consoleLog] - To ensure compatibility, wrapping your layout in LayoutWrappingEncoder.
16:33:36,714 |-WARN in ch.qos.logback.core.ConsoleAppender[consoleLog] - See also http://logback.qos.ch/codes.html#layoutInsteadOfEncoder for details
16:33:36,715 |-INFO in ch.qos.logback.classic.joran.action.RootLoggerAction - Setting level of ROOT logger to DEBUG
16:33:36,715 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [consoleLog] to Logger[ROOT]
16:33:36,716 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [person.pluto] to DEBUG
16:33:36,716 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - End of configuration.
16:33:36,716 |-INFO in ch.qos.logback.classic.joran.JoranConfigurator@42f30e0a - Registering current configuration as safe fallback point

16:33:36.722 [main      ] [p.p.n.s.client.ClientServiceThread           ] [INFO ] [79  ] - client service [10010] starting ...
16:33:36.728 [main      ] [p.p.n.s.client.ClientServiceThread           ] [INFO ] [95  ] - client service [10010] start success
16:33:36.731 [main      ] [p.p.n.s.listen.ServerListenThread            ] [INFO ] [48  ] - server listen port[8081] is created!
{"roomId":123,"clientId":123,"msg":"register","status":1}
address:/113.246.104.142port:18373
{"roomId":123,"clientId":456,"msg":"register","status":1}
address:/113.246.104.142port:19461
消息发送成功!
消息发送成功!
