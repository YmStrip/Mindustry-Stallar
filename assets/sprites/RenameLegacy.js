const fs = require('fs');
const path = require('path');


const targetDir = __dirname + "/distribution";

function renameFiles(dir) {
    if (!fs.existsSync(dir)) return;

    const files = fs.readdirSync(dir);

    files.forEach(oldNameWithExt => {
        const fullPath = path.join(dir, oldNameWithExt);
        if (!fs.lstatSync(fullPath).isFile()) return;

        // 【关键修复】：分离文件名和扩展名
        const {name, ext} = path.parse(oldNameWithExt);

        // 1. 处理 "-"：只修改第一个 "-" 之前的部分
        const parts = name.split('-');
        const mainPart = parts[0];
        const suffix = parts.length > 1 ? '-' + parts.slice(1).join('-') : '';

        // 2. 检查前缀是否为“全大写 + 下划线”
        const isUpperCaseUnderscore = /^[A-Z0-9_]+$/.test(mainPart);

        if (isUpperCaseUnderscore) {
            // 3. 转换为大驼峰
            const newMainPart = mainPart
                .split('_')
                .filter(word => word.length > 0)
                .map(word => {
                    // 如果单词全是字母且长度大于1，转为首字母大写
                    // 针对 A_B_C -> ABC 的特殊处理：如果单词很短，也可以直接拼接
                    return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
                })
                .join('');

            // 4. 拼接：新名 + 横杠后缀 + 原始扩展名
            const newName = newMainPart + suffix + ext;

            if (oldNameWithExt !== newName) {
                fs.renameSync(fullPath, path.join(dir, newName));
                console.log(`成功: ${oldNameWithExt} -> ${newName}`);
            }
        }
    });
}

function renameQIO(dir) {
    if (!fs.existsSync(dir)) return;

    const files = fs.readdirSync(dir);

    files.forEach(oldNameWithExt => {
        const fullPath = path.join(dir, oldNameWithExt);
        if (!fs.lstatSync(fullPath).isFile()) return;
        if (fullPath.includes("Qio")) {
            const newPath = fullPath.replace("Qio", "QIO")
            if (fullPath !== newPath) {
                fs.renameSync(fullPath, newPath);
                console.log(`成功: ${fullPath} -> ${newPath}`);
            }
        }
    });
}

renameFiles(targetDir);
//renameQIO(targetDir)