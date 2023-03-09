package co.com.nequi.mq.sender;
import co.com.bancolombia.commons.jms.api.MQMessageSender;
import co.com.nequi.model.modellistener.gateways.ModelListenerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import javax.jms.Message;

@RequiredArgsConstructor
@Slf4j
public class Sender implements ModelListenerRepository {

  private final MQMessageSender sender;

    @Override
    public Mono<String> send(String corId) {
            return sender.send(context ->
                    {
                        Message textMessage;
                        textMessage = context.createTextMessage("<NS1:esbXML xmlns:NS1=\"prueba\"><Header><systemId>BancaDigital</systemId><messageId>soyElmensaje</messageId></Header><Body><FIXML xsi:schemaLocation=\"http://www.finacle.com/fixml executeFinacleScript.xsd\" xmlns=\"http://www.finacle.com/fixml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:NS1=\"http://grupobancolombia.com/intf/IL/esbXML/V3.0\"> <Header> <ResponseHeader> <RequestMessageKey> <RequestUUID>TRNC57371589935253093</RequestUUID> <ServiceRequestId>executeFinacleScript</ServiceRequestId> <ServiceRequestVersion>10.6</ServiceRequestVersion> <ChannelId>COR</ChannelId> </RequestMessageKey> <ResponseMessageInfo> <BankId>1500</BankId> <TimeZone/> <MessageDateTime>2023-02-17T22:15:14.844</MessageDateTime> </ResponseMessageInfo> <UBUSTransaction> <Id/> <Status/> </UBUSTransaction> <HostTransaction> <Id/> <Status>SUCCESS</Status> </HostTransaction> <HostParentTransaction> <Id/> <Status/> </HostParentTransaction> <CustomInfo/> </ResponseHeader> </Header> <Body> <executeFinacleScriptResponse> <ExecuteFinacleScriptOutputVO/> <executeFinacleScript_CustomData> <acctDet> <loanAcctNum>87400000296</loanAcctNum> <loanAcctCrncyCode>COP</loanAcctCrncyCode> <disbAmt>5446500</disbAmt> <schmCode>PS4</schmCode> <loanStatus>R</loanStatus> <totNoOfInstl>24</totNoOfInstl> <totDmds>1</totDmds> <totDmdsPaid>1</totDmdsPaid> <totDmdsPending>0</totDmdsPending> <installmentAmt>247377.30</installmentAmt> <nextPaymentDate>24-01-2023</nextPaymentDate> <maturityDate>24-11-2024</maturityDate> </acctDet> <loanOutstandingDet> <princOutstanding>5230017.19</princOutstanding> <nrmlIntOutstanding>37812.18</nrmlIntOutstanding> <pnlIntOutstanding>0.00</pnlIntOutstanding> <feesOutstanding>12400.00</feesOutstanding> <netPayoffAmt>5735429.37</netPayoffAmt> </loanOutstandingDet> <loanDmdOutstandingDet> <princDmdOutstanding>0.00</princDmdOutstanding> <nrmlIntDmdOutstanding>0.00</nrmlIntDmdOutstanding> <pnlIntDmdOutstanding>0.00</pnlIntDmdOutstanding> <feesDmdOutstanding>3700.00</feesDmdOutstanding> </loanDmdOutstandingDet> <loanDmdDetails> <outLL> <serialNum>0001</serialNum> <flowDate>24-01-2023</flowDate> <princCompOutstnd>0</princCompOutstnd> <nrmlIntCompOutstnd>0</nrmlIntCompOutstnd> <penalIntCompOutstnd>0</penalIntCompOutstnd> <feeCompOutstnd>3700</feeCompOutstnd> </outLL> </loanDmdDetails> </executeFinacleScript_CustomData> </executeFinacleScriptResponse> </Body> </FIXML></Body></NS1:esbXML>");
                        textMessage.setJMSCorrelationID(corId);
                        return textMessage;
                    }).doOnError(i -> System.out.println("error" +i.getMessage()))
                    .doOnSuccess(i -> System.out.println("success"))
                    .name("mq_send_message")
                    .tag("operation", "my-operation")
                    .metrics();
    }

}
