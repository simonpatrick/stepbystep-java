
comment=$1
echo $comment

if [ ! -n "$comment" ]; then
    echo "is NUll"
    comment="make changes"
    echo $comment
fi

