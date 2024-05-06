const {Web3} = require('web3');
const fs = require('fs');

// 使用 Infura 的 URL 连接到 Sepolia 网络
const web3 = new Web3(new Web3.providers.HttpProvider('https://sepolia.infura.io/v3/69807b14381f41289b95aca892f860f3'));

// 合约 ABI 和地址
const contractAddress = '0xa8d63dA4771B2D96B068E4C366d887Bf867BB756';
const abiPath = './PetLottery_abi.json';
const abi = JSON.parse(fs.readFileSync(abiPath, 'utf-8'));

// 创建合约实例
const lotteryContract = new web3.eth.Contract(abi, contractAddress);

// 监听合约中的事件
lotteryContract.events.p({
    fromBlock: 'latest'
}, (error, event) => {
    if (error) console.error("Error in p event", error);
    else console.log("Event p triggered with value:", event.returnValues.t);
});

lotteryContract.events.LotteryRequested({
    fromBlock: 'latest'
}, (error, event) => {
    if (error) console.error("Error in LotteryRequested event", error);
    else console.log("LotteryRequested Event:", event.returnValues);
});

lotteryContract.events.LotteryFulfilled({
    fromBlock: 'latest'
}, (error, event) => {
    if (error) console.error("Error in LotteryFulfilled event", error);
    else console.log("LotteryFulfilled Event:", event.returnValues);
});

lotteryContract.events.HighLevelPetEnsured({
    fromBlock: 'latest'
}, (error, event) => {
    if (error) console.error("Error in HighLevelPetEnsured event", error);
    else console.log("HighLevelPetEnsured Event:", event.returnValues);
});

// 设置调用合约的账户和交易参数
const fromAddress = '0xa8d63dA4771B2D96B068E4C366d887Bf867BB756'; // 替换为你的钱包地址
const privateKey = '3717d74caa3a2fda97441578fdab4cfa56cc632f0f447d0a60a996c6a8f294f7'; // 替换为你的私钥

const amountToEnter = 1; // 例如，你想买1次彩票
const valueToSend = 10 * amountToEnter; // 根据lotteryFee计算发送的Ether数量

async function enterLottery() {
    const nonce = await web3.eth.getTransactionCount(fromAddress);
    const tx = {
        from: fromAddress,
        to: contractAddress,
        nonce: nonce,
        data: lotteryContract.methods.getRandomNumber(amountToEnter).encodeABI(),
        value: valueToSend,
        gas: 3000000
    };

    const signedTx = await web3.eth.accounts.signTransaction(tx, privateKey);
    web3.eth.sendSignedTransaction(signedTx.rawTransaction)
        .on('transactionHash', hash => {
            console.log("Transaction hash:", hash);
        })
        .on('receipt', receipt => {
            console.log("Transaction receipt:", receipt);
        })
        .on('error', error => {
            console.error("Transaction failed:", error);
        });
}

// 运行脚本
enterLottery();
